/**
 * Created by matthieu@ekai.io on 01/08/15.
 */

import conway.Conway

import scala.io.StdIn._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
package conway {

object Conway {
  def apply(r: String): Stream[String] = {
    def evolve(s: String): Stream[String] = {
      val e = s.split(" ").foldLeft(List[(String, Int)]()) { (cc, c) =>
        cc match {
          case (a, b) :: t if a == c => (a, b + 1) :: t
          case _ => (c, 1) :: cc
        }
      }

      val es = summary(e)
      es #:: evolve(es)
    }
    r #:: evolve(r)
  }

  def summary(l: List[(String, Int)]) = l.reverse.map { e => s"${e._2} ${e._1}" }.mkString(" ")
}

}

object Solution extends App {
  val r = readLine()
  val l = readInt()

  Console.println(Conway(r)(l - 1))
}