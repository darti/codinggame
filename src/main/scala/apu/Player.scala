import scala.io.StdIn._

/**
 * Don't let the machines win. You are humanity's last hope...
 **/


package apu {

case class Node(x: Int, y: Int, right: Option[Node], bottom: Option[Node]) {
  lazy val coord = s"$x $y"
  lazy val links = s"$coord ${right.fold("-1 -1")(_.coord)} ${bottom.fold("-1 -1")(_.coord)}"
}

trait Apu {

  case class LineState(line: List[Node], below: Map[Int, Node])
  case class State(lines: List[Node], below: Map[Int, Node])

  def chainLine(coord: (Int, Int), state: LineState) = {
    val left = state.line.headOption
    val bottom = state.below.get(coord._1)

    val cn = Node(coord._1, coord._2, left, bottom)
    LineState(cn :: state.line, state.below + (cn.x -> cn))
  }

  def buildGraph(input: Seq[String]) = {
    val nodes = input.zipWithIndex.foldRight(State(Nil, Map.empty)) { case ((l, y), state) =>

      val newLine = l.zipWithIndex.foldRight(LineState(Nil, state.below)) {
        case ((c, x), s) if c == '.' => s
        case ((c, x), s) => chainLine((x, y), s)
      }

      if(newLine.line.isEmpty) state
      else  State(newLine.line ++ state.lines, newLine.below)
    }

    nodes.lines
  }

  def links(nodes: List[Node]) = nodes map (_.links)
}

}

object Player extends App with apu.Apu {
  val width = readInt() // the number of cells on the X axis
  val height = readInt() // the number of cells on the Y axis

  val input = for(y <- 0 until height) yield readLine()

  //Console.err.println(input.mkString("\n"))

  val graph = buildGraph(input)
  links(graph) foreach println

}