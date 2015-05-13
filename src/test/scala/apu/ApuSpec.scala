package apu

import org.scalatest.FlatSpec

import scala.io.Source

/**
*  Created by matthieu@ekai.io on 26/04/15.
*/
class ApuSpec extends FlatSpec {

  behavior of "Test T"


  it should "be solved in XX steps" in {
    val hypo = teads.Solution.readHypothesis(readFile("Test_1_input.txt"))
    val solution = readSolution("Test_1_output.txt")

    val response = teads.Solution.solve

    assert(response === solution)

  }

}
