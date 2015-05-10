package teads

import org.scalatest.FlatSpec

import scala.io.Source

/**
*  Created by matthieu@ekai.io on 26/04/15.
*/
class TeadsSpec extends FlatSpec {

  behavior of "Test 1"

  def readFile(file: String) = {
    val url = getClass.getResource(file)
    Source.fromURL(url)
  }

  def readSolution(file: String) = {
    readFile(file).getLines().next().toInt
  }

  it should "be solved in XX steps" in {
    val hypo = teads.Solution.readHypothesis(readFile("Test_1_input.txt"))
    val solution = readSolution("Test_1_output.txt")

    val response = teads.Solution.solve

    assert(response === solution)

  }

}
