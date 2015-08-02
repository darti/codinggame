package numeros

import org.scalatest.{Matchers, FlatSpec}

import scala.io.Source

/**
 * Created by matthieu@ekai.io on 02/08/15.
 */
class Tests extends FlatSpec with Matchers {

  def numTest(pb: String, soluce: String) = {
    val nums = Source.fromInputStream(getClass.getResourceAsStream(pb)).getLines().toSeq.drop(1)
    val count = Source.fromInputStream(getClass.getResourceAsStream(soluce)).getLines().next().toInt

    nums mkString ", " should s"be $count" in {
      import numeros.Numeros

      assert(Numeros(nums) === count)
    }
  }

  for(i <- 1 to 5) numTest(s"Test_${i}_input.txt", s"Test_${i}_output.txt")
}
