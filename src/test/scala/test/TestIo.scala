package test

import play.api.libs.json.Json

import scala.io.Source
import scala.reflect.io.File

/**
 * Created by matthieu@ekai.io on 15/05/15.
 */


case class TestCase(name: String, problem: Seq[String], solution: Seq[String], skip: Option[Boolean]) {
  lazy val ignore = skip.getOrElse(false)
}

object TestCase {
  implicit val testCaseFormat = Json.format[TestCase]
}

trait TestIo {

  def readAll(name: String) = {
    val root = File(getClass.getResource(name).getPath).toDirectory

    val testCases = root.files map { f: File =>
      val s = Source.fromFile(f.jfile).getLines().mkString

      Json.parse(s).validate[TestCase]
    }

    testCases.flatMap {_.asOpt}.filter( ! _.ignore)
  }
}
