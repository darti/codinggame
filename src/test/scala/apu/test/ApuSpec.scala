package apu.test

import org.scalatest.{Matchers, FlatSpec}
import test.TestIo

/**
*  Created by matthieu@ekai.io on 26/04/15.
*/
class ApuSpec extends FlatSpec with Matchers with TestIo {

  val testCases = readAll("/apu")

  testCases foreach { tc =>
    val solver = new apu.Apu {}
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
    }
  }

}
