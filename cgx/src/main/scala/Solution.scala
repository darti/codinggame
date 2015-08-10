import scala.io.StdIn._

package cgx {

import scala.collection.immutable.Queue
import scala.util.matching.Regex


object Cgx {

  val indentSize = 4

  abstract class Element {
    def margin(level: Int, s: String) = " " * (indentSize * level) + s

    def format(level: Int = 0): String = ???
  }

  abstract class Value extends Element

  case class Bloc(elements: Seq[Element] = Nil) extends Value() {
    override def format(level: Int = 0) = {
      margin(level, "(") + (if (elements.isEmpty) "" else "\n") + {
        elements.map(_.format(level + 1)).mkString(";\n")
      } +
        "\n" + margin(level, ")")
    }
  }

  abstract class Primitive extends Value

  case class NumberValue(value: Int) extends Primitive {
    override def format(level: Int = 0) = margin(level, value.toString)
  }

  class Bool(value: Boolean) extends Primitive {
    override def format(level: Int = 0) = margin(level, value.toString)
  }

  case object TrueValue extends Bool(true)

  case object FalseValue extends Bool(false)

  case object NullValue extends Primitive

  case class StringValue(value: String) extends Primitive {
    override def format(level: Int = 0) = margin(level, s"'$value'")
  }

  case class KeyValue(clef: Element, value: Element) extends Element {
    override def format(level: Int) = margin(level, clef.format() + "=" + (value match { case Bloc(_) => "\n" + value.format(level); case _ => value.format()}))
  }

  case object Blank extends Element {
    override def format(level: Int): String = ""
  }

  case object BlocStart extends Element

  case object BlocEnd extends Element

  case object ListToken extends Element

  case object EqualToken extends Element


  object Parser {

    case class Rule(expr: Regex, f: (Regex.Match) => Element)

    val rules = List(
      Rule( """\s+""".r, _ => Blank),
      Rule("true".r, _ => TrueValue),
      Rule("false".r, _ => FalseValue),
      Rule("'(.*?)'".r, m => StringValue(m group 1)),
      Rule( """\(""".r, _ => BlocStart),
      Rule( """\)""".r, _ => BlocEnd),
      Rule("([0-9]+)".r, m => NumberValue((m group 1).toInt)),
      Rule(";".r, _ => ListToken),
      Rule("=".r, _ => EqualToken)
    )

    def lexer(s: CharSequence): List[Element] = {
      def applyRule(rs: List[Rule]): Option[(Element, CharSequence)] = rs match {
        case h :: t => (h.expr findPrefixMatchOf s) map (o => h.f(o) -> o.after) orElse applyRule(t)
        case Nil => None
      }

      if (s.length == 0) Nil
      else applyRule(rules) match {
        case Some((e, ss)) => e :: lexer(ss)
        case None => Nil
      }
    }

    type Tokens = List[Element]

    def parse(tokens: Tokens): (Tokens, Element) = tokens match {
      case (s @ StringValue(_)) :: EqualToken :: t =>
        val (nt, e) = parse(t)
        (nt, KeyValue(s, e))

      case BlocStart :: t =>
        val (nt, l) = parseBloc(t, List.empty)
        (nt, Bloc(l))

      case (p:  Primitive) :: t =>
        (t, p)
    }

    def parseBloc(tokens: Tokens, stack: List[Element]): (Tokens, List[Element]) = tokens match {
      case BlocEnd :: t =>
        (t, stack.reverse)

      case ListToken :: t =>
        val (nt, e) = parse(t)
        parseBloc(nt, e :: stack)

      case _ =>
        val (nt, e) = parse(tokens)
        parseBloc(nt, e :: stack)
    }

    def apply(s: String) = parse(lexer(s).filter{case Blank => false ; case _ => true})._2

  }

}

}

object Solution extends App {
  import cgx.Cgx._

  val n = readInt()
  val txt = (for(i <- 0 until n) yield readLine()).mkString("\n")

  println(Parser(txt).format())
}