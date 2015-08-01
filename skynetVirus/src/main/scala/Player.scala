import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Player extends App {
    // n: the total number of nodes in the level, including the gateways
    // l: the number of links
    // e: the number of exit gateways
    val Array(n, l, e) = for(i <- readLine split " ") yield i.toInt
    for(i <- 0 until l) {
        // n1: N1 and N2 defines a link between these nodes
        val Array(n1, n2) = for(i <- readLine split " ") yield i.toInt
    }
    for(i <- 0 until e) {
        val ei = readInt // the index of a gateway node
    }

    // game loop
    while(true) {
        val si = readInt // The index of the node on which the Skynet agent is positioned this turn
        
        // Write an action using println
        // To debug: Console.err.println("Debug messages...")
        
        println("0 1") // Example: 0 1 are the indices of the nodes you wish to sever the link between
    }
}