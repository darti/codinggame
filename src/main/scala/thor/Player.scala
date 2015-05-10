package thor

import scala.io.StdIn._
import scala.math._
/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Player extends App {
    // lx: the X position of the light of power
    // ly: the Y position of the light of power
    // tx: Thor's starting X position
    // ty: Thor's starting Y position
    val Array(lx, ly, tx, ty) = for(i <- readLine() split " ") yield i.toInt

  def move2Cardinal(dx: Int, dy: Int) = (dx, dy) match {
    case (-1, 0) => "W"
    case (1, 0) => "E"
    case (0, -1) => "N"
    case (0, 1) => "S"
    case (-1, -1) => "SW"
    case (-1, 1) => "NW"
    case (1, 1) => "SE"
    case (1, -1) => "NE"
    case _ => ""
  }

  val cmds = {
    def loop(px: Int, py: Int): Stream[(Int, Int, Int, Int, String)] = {
      val dx = signum(lx - px)
      val dy = signum(ly - py)
      val cmd = move2Cardinal(dx, dy)

      Console.err.println(s"($dx, $dy) => $cmd")

      (px, py, dx, dy, cmd) #:: loop(px + dx, py + dy)
    }

    loop(tx, ty)
  }

  cmds foreach { case (px, py, dx, dy, cmd) =>
    val e = readInt()

    Console.err.println(s"($px, $py) => ($dx, $dy) => $cmd")

    println(cmd)
  }
}