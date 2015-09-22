package supercalculateur

import org.scalatest.{Matchers, FlatSpec}

import scala.io.Source

/**
 * Created by matthieu@ekai.io on 19/09/15.
 */
class Test extends FlatSpec with Matchers {

  def scTest(n: Int, pb: String, soluce: String) = {
    val txt = Source.fromInputStream(getClass.getResourceAsStream(pb)).getLines().toSeq.drop(1)
    val tasks = SupCal.parse(txt)

    val sol = Source.fromInputStream(getClass.getResourceAsStream(soluce)).getLines().toSeq.head.toInt

    s"$n) ${tasks mkString " " }" should s"be $sol" in {
      assert(SupCal.solve(CompositeTask(tasks.toList)) === sol)
    }
  }


  for(i <- 1 to 3) scTest(i, s"Test_${i}_input.txt", s"Test_${i}_output.txt")
}
