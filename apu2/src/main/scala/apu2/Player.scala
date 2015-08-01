package apu2

import math._
import scala.io.StdIn._
import scala.util._

/**
 * The machines are gaining ground. Time to show them what we're really made of...
 **/
object Player extends App {
  val width = readInt() // the number of cells on the X axis
  val height = readInt() // the number of cells on the Y axis

  val input = for(y <- 0 until height) yield readLine()

  Console.err.println(input)

  // Write an action using println
  // To debug: Console.err.println("Debug messages...")

  println("0 0 2 0 1") // Two coordinates and one integer: a node, one of its neighbors, the number of links connecting them.
}