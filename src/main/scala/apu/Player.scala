import scala.io.StdIn._

/**
 * Don't let the machines win. You are humanity's last hope...
 **/

case class Node(x: Int, y: Int, right: Option[Node], bottom: Option[Node]) {
  lazy val coord = s"$x $y"
  lazy val links = s"$coord ${right.fold("-1 -1")(_.coord)} ${bottom.fold("-1 -1")(_.coord)}"
}

trait Solution {
  def buildGraph(input: String): Unit = {

  }
}

object Player extends App with Solution {
  val width = readInt() // the number of cells on the X axis
  val height = readInt() // the number of cells on the Y axis



  def graph(nodes: Seq[Seq[Node]]) = {
    val g = nodes.filter{_.size > 0}.foldRight(List[List[Node]]()) { (nl: Seq[Node], accl: List[List[Node]]) =>

      val newLine = nl.foldRight(
        (List[Node](), accl.headOption.map {_.reverse})) {
        (n: Node, acc: (List[Node], Option[List[Node]])) =>
          acc._2 match {
            case Some((bn @ Node(x, _, _, _)) :: t) if x == n.x => (Node(n.x, n.y, acc._1.headOption, Some(bn)) :: acc._1, Some(t))
            case _ => (Node(n.x, n.y, acc._1.headOption, None) :: acc._1, acc._2)
          }

      }

      newLine._1 :: accl
    }

    g
  }

  val grid = graph(
    for(y <- 0 until height) yield readLine().zipWithIndex.flatMap {case (v, x) => if(v.equals('0')) Some(Node(x, y, None, None)) else None}
  )

  Console.err.println(grid.mkString("\n"))


  grid.flatten foreach {n =>
    println(s"${n.links}")
  }

}