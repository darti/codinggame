package descente

import scala.io.StdIn._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Player extends App {

    // game loop
    while(true) {
      val Array(sx, sy) = for(i <- readLine() split " ") yield i.toInt

      val heights = (0 until 8).map(_ => readInt())

      val (th, tx) = heights.zipWithIndex.foldLeft((0,0)) {(a, b) => if(b._1 > a._1) b else a}

      println(
        if(tx == sx) "FIRE" else "HOLD"
      )
    }
}