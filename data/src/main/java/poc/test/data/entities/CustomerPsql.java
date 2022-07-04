package poc.test.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "poc")
public class CustomerPsql {

    public CustomerPsql(UUID id) {
        this.id = id;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "customer")
    private List<OrderPsql> orders = new ArrayList<>();

}
