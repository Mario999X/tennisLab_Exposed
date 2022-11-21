package repositories.adquisicion

import models.Adquisicion
import repositories.CrudRepository

interface AdquisicionRepository : CrudRepository<Adquisicion, Long> {
}