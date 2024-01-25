import com.anon.menu.domain.entities.User
import com.anon.menu.domain.utils.TokenDecrypted

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

const val AUTHORITIES = "authorities"

object JWTUtils {
    fun generate(
        user: User,
        secret: String,
        expiration: String
    ): String {
        val zoneDateTime = LocalDateTime.now().plusHours(expiration.toLong()).atZone(ZoneId.systemDefault())
        val expirationDate = Date.from(zoneDateTime.toInstant())

        return Jwts.builder()
            .setSubject(user.id)
            .setIssuer("Anon")
            .claim(AUTHORITIES, listOf(user.role))
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun getClaims(token: String, secret: String): TokenDecrypted {
        val claims = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
        return TokenDecrypted(claims)
    }
}