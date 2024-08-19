import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class Event {
    private int id;
    private String name;
    private LocalDate date;
    private String description;

    public Event(int id, String name, LocalDate date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}

package com.eventplanner;


public class EventPlanner {
    private List<Event> events;

    public EventPlanner() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        if (events.stream().anyMatch(e -> e.getId() == event.getId())) {
            throw new IllegalArgumentException("Event with this ID already exists.");
        }
        events.add(event);
    }

    public void updateEvent(int id, Event updatedEvent) {
        Optional<Event> eventOptional = events.stream()
                                              .filter(event -> event.getId() == id)
                                              .findFirst();
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            events.remove(event);
            events.add(updatedEvent);
        } else {
            throw new IllegalArgumentException("Event with ID " + id + " not found.");
        }
    }

    public void removeEvent(int id) {
        boolean removed = events.removeIf(event -> event.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Event with ID " + id + " not found.");
        }
    }

    public List<Event> listEvents() {
        return new ArrayList<>(events);
    }
}
public class EventPlannerTest {

    private EventPlanner eventPlanner;
    public void setUp() {
        eventPlanner = new EventPlanner();
    }
    public void testAddEvent() {
        Event event = new Event(1, "Birthday Party", LocalDate.of(2024, 8, 20), "A fun birthday party.");
        eventPlanner.addEvent(event);
        List<Event> events = eventPlanner.listEvents();
        assertEquals(1, events.size());
        assertEquals("Birthday Party", events.get(0).getName());
        Event duplicateEvent = new Event(1, "Anniversary", LocalDate.of(2024, 9, 15), "An anniversary party.");
        assertThrows(IllegalArgumentException.class, () -> eventPlanner.addEvent(duplicateEvent));
    }
    public void testUpdateEvent() {
        Event event = new Event(1, "Birthday Party", LocalDate.of(2024, 8, 20), "A fun birthday party.");
        eventPlanner.addEvent(event);
        Event updatedEvent = new Event(1, "Wedding", LocalDate.of(2024, 9, 20), "A beautiful wedding.");
        eventPlanner.updateEvent(1, updatedEvent);
        List<Event> events = eventPlanner.listEvents();
        assertEquals("Wedding", events.get(0).getName());
        assertThrows(IllegalArgumentException.class, () -> eventPlanner.updateEvent(2, updatedEvent));
    }
    public void testRemoveEvent() {
        Event event1 = new Event(1, "Birthday Party", LocalDate.of(2024, 8, 20), "A fun birthday party.");
        Event event2 = new Event(2, "Conference", LocalDate.of(2024, 9, 10), "A tech conference.");
        eventPlanner.addEvent(event1);
        eventPlanner.addEvent(event2);
        eventPlanner.removeEvent(1);
        List<Event> events = eventPlanner.listEvents();
        assertEquals(1, events.size());
        assertEquals("Conference", events.get(0).getName());
        assertThrows(IllegalArgumentException.class, () -> eventPlanner.removeEvent(3));
    }
    public void testListEvents() {
        Event event1 = new Event(1, "Birthday Party", LocalDate.of(2024, 8, 20), "A fun birthday party.");
        Event event2 = new Event(2, "Conference", LocalDate.of(2024, 9, 10), "A tech conference.");
        eventPlanner.addEvent(event1);
        eventPlanner.addEvent(event2);
        List<Event> events = eventPlanner.listEvents();
        assertEquals(2, events.size());
        assertTrue(events.contains(event1));
        assertTrue(events.contains(event2));
    }
}
