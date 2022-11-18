package repositories.personalizar

import models.Personalizar
import repositories.CrudRepository

interface PersonalizarRepository : CrudRepository<Personalizar, Long> {
}