package  com.anon.menu.domain.entities

import io.azam.ulidj.ULID
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Item(
    @Id
    val id: String = ULID.random(),
    val name: String,
    val description: String,
    val price: BigDecimal,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    val restaurant: Restaurant
)
