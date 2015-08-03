import scala.io.StdIn._


package cgx {

  object Cgx {

    val indentSize = 4

    abstract class Element {
      def format(level: Int = 0): String = ""

      def margin(level: Int) = " " * (indentSize * level)

      lazy val isNothing = false
    }

    abstract class Valeur extends Element

    case class Bloc(elements: Seq[Element]) extends Valeur() {
      override def format(level: Int = 0) =
        s"""${margin(level)}(""" + (if(elements.isEmpty) "" else "\n") +
           {elements.map(_.format(level + 1)).mkString(";\n")} +
           s"""\n${margin(level)})"""
    }

    abstract class TypePrimitif extends Valeur()

    case class Nombre(value: Int) extends TypePrimitif {
      override def format(level: Int = 0) = margin(level) + value.toString
    }

    case class Bool(value: Boolean) extends TypePrimitif {
      override def format(level: Int = 0) = margin(level) + value.toString
    }

    case object Null extends TypePrimitif

    case class Chaine(value: String) extends TypePrimitif {
      override def format(level: Int = 0) = s"${margin(level)}'$value'"
    }

    case class ClefValeur(clef: Chaine, valeur: Valeur)

    case object Nothing extends Element {
      override def format(level: Int): String = ""

      override lazy val isNothing: Boolean = true
    }

    case object Rules {
      val bool = """\s*(true|false)\s*"""
      val rBool = bool.r("value")

      val string = """\s*'([^']*)'\s*"""
      val rString = string.r("value")

      val int = """\s*([0-9]+)\s*"""
      val rInt = int.r("value")

      val bloc = """\s*\((.*)\)\s*"""
      val rBloc = bloc.r("content")

      val primitive = s"""$bloc|$bool|$string|$int"""
      val list = s"""($primitive(;$primitive)*)"""
      val rList = list.r

      val rNothing = """(\s*)""".r
    }

    def splitBloc(content: String) = {
      val parts = content split ";"
      def debt(s: String) = ("""\)""".r findAllIn s).size -  ("""\)""".r findAllIn s).size

      def merge(d: Int, current: String, ss: Seq[String]): List[String] = ss match {
        case h :: t =>
          val nd = d + debt(h)
          if(nd == 0) (current + h) :: merge(0, "", t) else merge(nd, current + h, t)
        case Nil => Nil
      }
      //
      merge(0, "", parts.toList)
    }

    def parse(s: String): Element = {
      import Rules._

      s match {
        case rBloc(content) =>
          val sb = splitBloc(content)
          Bloc(sb map parse filterNot(_.isNothing))
        case rBool(value) => Bool(value.toBoolean)
        case rString(value) => Chaine(value)
        case rInt(value) => Nombre(value.toInt)
        case rNothing => Nothing
      }
    }
  }
}

object Solution extends App {
  import cgx.Cgx._

  val n = readInt()
  val txt = (for(i <- 0 until n) yield readLine()).mkString("\n")

  println(parse(txt).format())
}