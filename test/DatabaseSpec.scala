import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.routing.Router
import play.api.test.Injecting
import reactivemongo.api.indexes.Index
import reactivemongo.api.indexes.IndexType.Ascending
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext

class DatabaseSpec extends PlaySpec with ScalaFutures with GuiceOneAppPerSuite with Injecting with BeforeAndAfterAll with IntegrationPatience{
  override def fakeApplication() = GuiceApplicationBuilder().router(Router.empty).build()

  lazy val db = inject[Database]
  implicit lazy val ec = inject[ExecutionContext]


  override protected def beforeAll() = {
    db.col.drop(failIfNotFound = false).futureValue
  }

  "should read from a non-existing collection" in {
    db.col.find(BSONDocument.empty).one[BSONDocument].futureValue mustBe empty
  }

  // This tests fails
  "should ensure index (collection doesn't exits" in {
    db.col.indexesManager.ensure(Index(key = Seq("a" -> Ascending), background = true)).futureValue mustBe true
  }

  "should insert document" in {
    db.col.insert(BSONDocument("a" -> 1)).futureValue.n mustBe 1
  }

  "should ensure index (collection exist)" in {
    db.col.indexesManager.ensure(Index(key = Seq("a" -> Ascending), background = true)).futureValue mustBe true
  }

}
