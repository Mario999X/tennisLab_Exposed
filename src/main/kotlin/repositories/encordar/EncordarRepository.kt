package repositories.encordar

import models.Encordar
import repositories.CrudRepository

interface EncordarRepository : CrudRepository<Encordar, Long> {
}