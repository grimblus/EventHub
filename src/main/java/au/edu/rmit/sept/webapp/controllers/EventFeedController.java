package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Locale;
import java.util.Map;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.services.UserInterestsService;
import au.edu.rmit.sept.webapp.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.services.EventParticipantsService;
import au.edu.rmit.sept.webapp.services.EventService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class EventFeedController {

    private EventService eventService;
    private UserInterestsService userInterestsService;

    @Value("${app.uploads.root:uploads}")
    private String uploadsRoot;
    private EventParticipantsService eventParticipantsService;
    private UserService userService;

    public EventFeedController(EventService eventService, 
                                UserInterestsService userInterestsService, 
                                EventParticipantsService eventParticipantsService,
                                UserService userService ) {
        this.eventService = eventService;
        this.userInterestsService = userInterestsService;
    }

    // check for user permission, admin or OWNER organiser
    private boolean canEditEvent(HttpSession session, int eventId) {
        User user = (User) session.getAttribute("user");
        if (user == null) return false;

        var role = user.getPermissionLevel();
        if (role == User.permissions.ADMIN) return true;

        if (role == User.permissions.ORGANISER) {
            Integer ownerId = eventService.getOwnerID(eventId);
            return ownerId != null && ownerId.equals(user.getId());
        }

        return false;
    }

    @GetMapping("/events")
    public String events(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> keywords,
            Model model,
            HttpSession session
    ) {
        // Find out how to do tags and stuff
        User user = (User) session.getAttribute("user");
        List<Event> events =  eventService.getEventsByFilter(search, tags, location, category, keywords);

        //if user is  logged in, display recommended events
        if (user != null) {
            //hashset so that checking for an interest is O(1) time B)
            HashSet<String> userInterests = userInterestsService.getUserInterests(user.getId());
            List<Event> recommendedEvents = new ArrayList<>();

            //for each event
            for ( Event event : events) {
//                System.out.println("Event:" + event.getTitle());
//                System.out.println(event.getCategory());
                //if the event's category is the user's interest, add it it to the list to display
                if (userInterests.contains(event.getCategory().toUpperCase())) {
                    recommendedEvents.add(event);

                }
                //if didn't find a category interested in, then check the tags
                else  {
                    //for each of the event's tags
                    for ( String tag : event.getTags()) {
                       // System.out.println(tag);

                        //if the event's tag is one of the user's interest, add the event to the list
                        // and break as well because only one interest needs to be found
                        if (userInterests.contains(tag.toUpperCase())) {
                            recommendedEvents.add(event);
                            break;
                        }
                    }
                }
            }
            model.addAttribute("recommendedEvents", recommendedEvents);
        }

        model.addAttribute("events", events);
        model.addAttribute("tags", eventService.getTags());
        model.addAttribute("keywords", eventService.getKeywords());

        return "events/index";
    }

    // checks if the user is the owner of the event
    @GetMapping("/events/modal/{eventId}")
    @ResponseBody
    public Map<String, Object> checkOwner(@PathVariable Integer eventId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean isOwner = false;
        boolean isAdmin = false;
        boolean canEdit = false;

        if (user != null) {
            isAdmin = user.getPermissionLevel() == User.permissions.ADMIN;

            if (user.getPermissionLevel() == User.permissions.ORGANISER) {
                Integer ownerId = eventService.getOwnerID(eventId);
                isOwner = ownerId != null && ownerId.equals(user.getId());
            }

            // Admin can always edit; organiser only if owner
            canEdit = isAdmin || isOwner;
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("isOwner", isOwner);
        resp.put("isAdmin", isAdmin);
        resp.put("canEdit", canEdit);
        return resp;
    }

    // event details for modal
    @PostMapping("/events")
    public String eventDetails(
            Integer id, 
            Model model
    ) {
        model.addAttribute("modal-content", eventService.findEventbyID(id));
        return "events/index";
    }

    // delete images (also remove the file on disk if we can)
    @DeleteMapping("/events/{eventId}/images/{index}")
    public ResponseEntity<Void> deleteImage(@PathVariable int eventId,
                                            @PathVariable int index,
                                            HttpSession session) {
        if (!canEditEvent(session, eventId)) {
            return ResponseEntity.status(403).build();
        }
        // get current filename before removal (so we can delete from disk)
        var ev = eventService.findEventbyID(eventId);
        String filename = null;
        if (ev != null && ev.getImages() != null && index >= 0 && index < ev.getImages().size()) {
            filename = ev.getImages().get(index);
        }

        boolean ok = eventService.deleteImageForEvent(eventId, index);
        if (!ok) return ResponseEntity.notFound().build();

        if (filename != null) {
            try {
                Path p = Path.of(uploadsRoot, "events", String.valueOf(eventId), filename)
                            .toAbsolutePath().normalize();
                Files.deleteIfExists(p);
            } catch (Exception ignored) {}
        }
        return ResponseEntity.noContent().build();
    }

    // add images
    @PostMapping("/events/{eventId}/images")
    @ResponseBody
    public ResponseEntity<?> addImage(@PathVariable int eventId,
                                    @RequestParam("file") MultipartFile file,
                                    HttpSession session) {
        if (!canEditEvent(session, eventId)) {
            return ResponseEntity.status(403).body("Forbidden");
        }
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty file");
        }
        String ct = file.getContentType();
        if (ct == null || !ct.startsWith("image/")) {
            return ResponseEntity.badRequest().body("Only images allowed");
        }

        try {
            Path dir = Path.of(uploadsRoot, "events", String.valueOf(eventId)).toAbsolutePath().normalize();
            Files.createDirectories(dir);

            String ext = safeExt(file.getOriginalFilename());
            String basename = java.util.UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

            Path dest = dir.resolve(basename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            boolean ok = eventService.addImageForEvent(eventId, basename);
            if (!ok) return ResponseEntity.notFound().build();

            String url = "/uploads/events/" + eventId + "/" + basename;
            return ResponseEntity
                    .created(java.net.URI.create(url))
                    .body(java.util.Map.of("url", url, "filename", basename));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }


    // helper for safe extension
    private String safeExt(String originalName) {
        if (originalName == null) return "";
        String name = org.springframework.util.StringUtils.getFilename(originalName);
        if (name == null) return "";
        int dot = name.lastIndexOf('.');
        if (dot < 0) return "";
        String ext = name.substring(dot + 1).toLowerCase(Locale.ROOT);
        return switch (ext) {
            case "png","jpg","jpeg","gif","webp","bmp","svg" -> ext;
            default -> "";
        };
    }

    // export CSV of people attending event
    @PostMapping("/events/{eventId}/export")
    public ResponseEntity<Void> exportCSV(@RequestParam("eventId") int eventId,
                                          @RequestParam("eventTitle") String eventTitle,
                                            Model model,
                                            HttpServletResponse response) {
        
        // I DONT LIKE HOW THIS CODE IS HERE, I WANT IT IN THE FUNCTION, BUT IT REQUIRES THE SERVLET RESPONSE
        // AND I CANNOT PASS THAT THROUGH AS A PARAMETER, MAYBE THE FUNCTION CAN RETURN A STRING TO BE PRINTED?
                
        List<Integer> a_ids = eventParticipantsService.getEventParticipants(eventId);

        response.setContentType("text/csv;  charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + eventTitle + "-attendees.csv");
        
        try(PrintWriter writer = response.getWriter()) {
            writer.println("ID,FirstName,LastName,Email");
            for(int i = 0; i < a_ids.size(); ++i) {
                User attendee = userService.findUserByID(a_ids.get(i));

                String row = String.format(Locale.ROOT, "%d,%s,%s,%s",
                    attendee.getId(),
                    attendee.getFirstName(),
                    attendee.getLastName(),
                    attendee.getEmail()
                );
                writer.println(row);
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        return eventParticipantsService.PrintAttendeeCSV(eventId) 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.notFound().build();
    }

    // delete event
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventId) {

        

        return eventService.deleteEventbyID(eventId)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

}
