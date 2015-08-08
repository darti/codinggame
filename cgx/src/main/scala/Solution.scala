import scala.io.StdIn._

package cgx {

import scala.annotation.tailrec
import scala.collection.immutable.Queue


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
      s"""${margin(level)}(""" + (if (elements.isEmpty) "" else "\n") + {
        elements.map(_.format(level + 1)).mkString(";\n")
      } +
        s"""\n${margin(level)})"""
  }

  abstract class TypePrimitif extends Valeur()

  case class Nombre(value: Int) extends TypePrimitif {
    override def format(level: Int = 0) = margin(level) + value.toString
  }

  class Bool(value: Boolean) extends TypePrimitif {
    override def format(level: Int = 0) = margin(level) + value.toString
  }

  case object Vrai extends Bool(true)

  case object Faux extends Bool(false)

  case object Nulle extends TypePrimitif

  case class Chaine(value: String) extends TypePrimitif {
    override def format(level: Int = 0) = s"${margin(level)}'$value'"
  }

  case class ClefValeur(clef: Chaine, valeur: Valeur)

  case object Nothing extends Element {
    override def format(level: Int): String = ""

    override lazy val isNothing: Boolean = true
  }


  val trueRegex = "(true)(.*)".r("true", "tail")

  def parse(expr: String) = {

    /*@tailrec*/ def lexer(q: List[Element], s: String): (List[Element], String) = s.headOption match {
        case Some('t') =>
          lexer(Vrai :: q, s.stripPrefix("true"))

        case Some('f') =>
          lexer(Faux :: q, s.stripPrefix("false"))

        case Some('n') =>
          lexer(Nulle :: q, s.stripPrefix("null"))

        case Some('\'') =>
          val se = s.indexOf("'", 1)
          val (c, t) = s.splitAt(se)
          lexer(Chaine(c.tail) :: q, t.tail)

        case Some('(') =>
          val (l, ss) =  lexer(List.empty, s.tail)
          lexer(Bloc(l.reverse) :: q, ss)

        case Some(')') =>
          (q, s.tail)

        case Some(';') =>
          val (l, ss) =  lexer(List.empty, s.tail)
          (l ++ q, ss)

        case Some('=') =>
          lexer(q, s.tail)

        case None => (q, s)
        case Some(c) if c.isDigit =>
          val n = s.prefixLength(_.isDigit)
          val (i, t) = s.splitAt(n)

          lexer(Nombre(i.toInt) :: q, t)
        case _ => lexer(q, s.tail)
      }

    val (el, s) = lexer(List.empty, expr)
    el.head
  }
}

}

object Solution extends App {
  import cgx.Cgx._

  val n = readInt()
  val txt = (for(i <- 0 until n) yield readLine()).mkString("\n")

  println(parse(txt).format())
}