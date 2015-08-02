import scala.io.StdIn._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

package numeros {

  object Numeros {
    private[this] def walk(num: Seq[String]): Int = {
        val subNums = num flatMap { s => s.headOption.map( _ -> s.drop(1)) } groupBy(_._1)
        1 + subNums.values.map {l => walk( l map(_._2))}.sum
      }

    def apply(num: Seq[String]) = walk(num) - 1
  }
}

object Solution extends App {
  import numeros.Numeros

  val n = readInt()
  val nums = for(i <- 0 until n) yield readLine()

  println(Numeros(nums))
}