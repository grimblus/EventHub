package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.entity.Event;
import au.edu.rmit.sept.webapp.repository.EventRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestEventServiceImpl {
    Event event = new Event();

    @Mock
    EventRepository eventRepository;

    @InjectMocks
    EventServiceImpl eventService;

    @Test
    void testStringList() {
        String testString = "";
        List<String> strings = List.of(testString);
        when(eventRepository.stringList(testString)).thenReturn(strings);

        List<String> result = eventService.stringList(testString);

        assertEquals(strings, result);
    }

    @Test
    void testAddEvent() {
        eventService.addEvent(event);
        verify(eventRepository).addEvent(event);
    }

    @Test
    void testGetEvents() {
        List<Event> events = List.of(event);
        when(eventRepository.getEvents()).thenReturn(events);

        List<Event> result = eventService.getEvents();

        assertEquals(events, result);
    }

    @Test
    void testGetEvent() {
        int testId = 1;
        String testString = "test";
        when(eventRepository.getEvent(testId, testString)).thenReturn(event);
        Event result = eventService.getEvent(testId, testString);
        assertEquals(event, result);
    }

    @Test
    void testGetEventsByFilter() {
        List<Event> events = List.of(event);
        when(eventRepository.getEventsByFilter("test", null, null, null, null)).thenReturn(events);

        List<Event> result = eventService.getEventsByFilter("test", null, null, null, null);

        assertEquals(events, result);
    }

    @Test
    void testGetTags() {
        String testString = "";
        List<String> tags = List.of(testString);
        when(eventRepository.getTags()).thenReturn(tags);

        List<String> result = eventService.getTags();

        assertEquals(tags, result);
    }

    @Test
    void testGetKeywords() {
        String testString = "";
        List<String> keywords = List.of(testString);
        when(eventRepository.getKeywords()).thenReturn(keywords);

        List<String> result = eventService.getKeywords();

        assertEquals(keywords, result);
    }

    @Test
    void testFindEventbyID() {
        int testId = 1;
        when(eventRepository.findEventbyID(testId)).thenReturn(event);
        Event result = eventService.findEventbyID(testId);
        assertEquals(event, result);
    }

    @Test
    void testDeleteEventbyID() {
        int testId = 1;
        boolean testDelete = false;
        when(eventRepository.deleteEventbyID(testId)).thenReturn(testDelete);
        boolean result = eventService.deleteEventbyID(testId);
        assertEquals(testDelete, result);
    }

    @Test
    void testGetOwnerID() {
        int testId = 1;
        int testOwnerId = 2;
        when(eventRepository.getOwnerID(testId)).thenReturn(testOwnerId);
        Integer result = eventService.getOwnerID(testId);
        assertEquals(testOwnerId, result);
    }

    @Test
    void testDeleteImageForEvent() {
        int testId = 1;
        int index = 1;
        boolean testDelete = false;
        when(eventRepository.deleteImageForEvent(testId, index)).thenReturn(testDelete);
        boolean result = eventService.deleteImageForEvent(testId, index);
        assertEquals(testDelete, result);
    }

    @Test
    void testAddImageForEvent() {
        int testId = 1;
        String testString = "test.png";
        boolean testAdd = false;
        when(eventRepository.addImageForEvent(testId, testString)).thenReturn(testAdd);
        boolean result = eventService.addImageForEvent(testId, testString);
        assertEquals(testAdd, result);
    }
}
