package repositories.raqueta

import models.Raqueta
import repositories.CrudRepository

interface RaquetaRepository : CrudRepository<Raqueta, Long> {
}