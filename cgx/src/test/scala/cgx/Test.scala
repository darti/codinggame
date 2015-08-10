package cgx

import org.scalatest.{Matchers, FlatSpec}

import scala.collection.immutable.Queue
import scala.io.Source

/**
 * Created by matthieu@ekai.io on 03/08/15.
 */
class Test extends FlatSpec with Matchers {

  def cgxTest(n: Int, pb: String, soluce: String) = {
    val txt = Source.fromInputStream(getClass.getResourceAsStream(pb)).getLines().toSeq.drop(1).mkString("\n")
    val sol = Source.fromInputStream(getClass.getResourceAsStream(soluce)).getLines().toSeq.mkString("\n")

    s"$n) $txt" should s"be formated in\n$sol" in {

      val tree = Cgx.Parser(txt)
      assert(tree.format() === sol)
    }
  }


  for(i <- 1 to 12) cgxTest(i, s"Test_${i}_input.txt", s"Test_${i}_output.txt")
}

