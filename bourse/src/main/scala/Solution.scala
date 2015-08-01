
import scala.io.StdIn._
import math._
import scala.language.postfixOps

/**
 * Created by matthieu@ekai.io on 29/07/15.
 */
object Solution extends App {
  val n = readInt()
  val vs = readLine()

  val ts = vs split " " map (_.toInt) toList

  case class Path(high: Int, low: Int) {
    lazy val loss = low - high
  }

  case class DownPath(best: Path, current: Path) {
    val add: PartialFunction[Int, DownPath] = {
      case p if p > current.high => DownPath(best, Path(p, p))
      case p if p < current.low =>
        val np = Path(current.high, p)
        if(np.loss < best.loss) DownPath(np, np) else DownPath(best, np)

      case _ => this
    }
  }

  Console.err.println(ts)

  val loss = ts match {
    case h :: t =>
      val dp = t.foldLeft(DownPath(Path(h, h), Path(h, h))) { (d, v) => d.add(v) }
      dp.best.loss
    case Nil => 0
  }

  println(loss)
}