package conway

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by matthieu@ekai.io on 01/08/15.
 */
class Test extends FlatSpec with Matchers {

  def test(r: String, l: Int, s: String) = s"r=$r l=$l" should s"be $s" in {
    val serie = conway.Conway(r)

    assert(serie(l - 1) === s)
  }


  test("1", 1, "1")
  test("1", 2, "1 1")
  test("1", 3, "2 1")
  test("1", 4, "1 2 1 1")
  test("1", 5, "1 1 1 2 2 1")

}
