import scala.io.StdIn._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

package numeros {

  object Numeros {
    def apply(num: Seq[String]): Int = {

    }
  }
}

object Solution extends App {
  import numeros.Numeros

  val n = readInt()
  val nums = for(i <- 0 until n) yield readLine()

  println(Numeros(nums))
}