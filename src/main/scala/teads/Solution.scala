package teads

import scala.io.Source
import scala.io.StdIn._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Solution extends App {

    def readHypothesis(source: Source) = {
        val lines = source.getLines()
        val n = lines.next().toInt

        lines take n flatMap { l =>
            val Array(xi, yi) = for (i <- l split " ") yield i.toInt
            Seq(xi -> yi, yi -> xi)
        }
    }

    def solve = {

        1
    }

    solve
}