package apu2.test

import org.scalatest.{FlatSpec, Matchers}
import test.TestIo

/**
*  Created by matthieu@ekai.io on 26/04/15.
*/
class Apu2Spec extends FlatSpec with Matchers with TestIo {

  val testCases = readAll("/apu2")

  testCases foreach { tc =>
    /*val solver = new apu.Apu {}
    val graph = solver.buildGraph(tc.problem)
    val solution = solver.links(graph)

    behavior of tc.name

    tc.solution foreach { s =>
      it should s"print $s" in {
        solution should contain (s)
      }

    }

    it should "not print anything else"  in {
      solution foreach { s =>
        assert(tc.solution contains s)
      }
    }*/
  }

}
