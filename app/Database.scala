import javax.inject.{Inject, Singleton}

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.DefaultDB
import reactivemongo.api.collections.bson.BSONCollection

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration

@Singleton
class Database @Inject()(
  implicit val ec: ExecutionContext,
  val reactiveMongo: ReactiveMongoApi
) {
  val database: DefaultDB = Await.result(reactiveMongo.database, Duration.Inf)
  val col: BSONCollection = database.collection[BSONCollection]("col")
  println(Await.result(col.count(), Duration.Inf))
}
