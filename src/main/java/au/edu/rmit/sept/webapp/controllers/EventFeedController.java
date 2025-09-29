package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.edu.rmit.sept.webapp.services.EventService;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;

@Controller
public class EventFeedController {

    private EventService eventService;

    @Value("${app.uploads.root:uploads}")
    private String uploadsRoot;

    public EventFeedController(EventService eventService) {this.eventService = eventService;}

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
        model.addAttribute("events", eventService.getEventsByFilter(search, tags, location, category, keywords));
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

        //If the owner is an organiser and the user exists, checks if user id matches with the event's owner id
        if (user != null && user.getPermissionLevel() == User.permissions.ORGANISER) {
            Integer ownerId = eventService.getOwnerID(eventId);
            if (ownerId != null && ownerId.equals(user.getId())) {
                isOwner = true;
            }
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("isOwner", isOwner);
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
    public ResponseEntity<Void> deleteImage(@PathVariable int eventId, @PathVariable int index) {
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
                                    @RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Empty file");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only images allowed");
            }

            // uploads/events/{eventId}/
            Path dir = Path.of(uploadsRoot, "events", String.valueOf(eventId)).toAbsolutePath().normalize();
            Files.createDirectories(dir);

            // unique filename with safe extension
            String ext = safeExt(file.getOriginalFilename());
            String basename = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

            Path dest = dir.resolve(basename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            // store ONLY the basename in DB (matches your repository contract)
            boolean ok = eventService.addImageForEvent(eventId, basename);
            if (!ok) return ResponseEntity.notFound().build();

            String url = "/uploads/events/" + eventId + "/" + basename; // browser-loadable path
            return ResponseEntity.ok(Map.of("url", url, "filename", basename));
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

    // delete event
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventId) {

        

        return eventService.deleteEventbyID(eventId)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

}
