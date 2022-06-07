import lombok.Builder;

@Builder
public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;
}