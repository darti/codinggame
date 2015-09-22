
import scala.io.StdIn._



package supercalculateur {

  trait Task {
    def start: Int
    def end: Int

    def toList: List[Task]

    def merge(task: Task) = CompositeTask(this.toList ++ task.toList)

    def merge(tasks: List[Task]) = CompositeTask((this :: tasks) flatMap(_.toList))

    def overlap(task: Task) = (start <= task.end && end >= task.start) || (task.start <= end && task.end >= start)

    def contains(task: Task) = start <= task.start && end >= task.end

    def count: Int

    def scheduled: Boolean
  }

  case class SingleTask(override val start: Int, duration: Int) extends Task {
    override lazy val end = start + duration - 1

    override lazy val toList = this :: Nil

    override def count = 1

    override def scheduled = true
  }

  case class CompositeTask(tasks: List[Task]) extends Task {
    override lazy val start = tasks.minBy(_.start).start

    override lazy val end = tasks.maxBy(_.end).end

    override def toList = tasks

    override def count = tasks.length

    lazy val scheduled = tasks flatMap {t => t.start to t.end} groupBy identity forall (_._2.length <= 1)
  }

  object SupCal {
    def parse(lines: Seq[String]) = lines map(_ split " ") map {
        case Array(j, d) => SingleTask(j.toInt, d.toInt)
    }

    def solve(tasks: CompositeTask): Int = tasks.tasks match {
      case _ if tasks.scheduled => tasks.count
      case _ =>
        val st = cluster(tasks.tasks)
        val sst = st map { t => CompositeTask(trim(t.toList)) }

        if (sst.length == 1)
          walk(sst.head)
        else
          (sst map solve).sum

    }

    def walk(ct: CompositeTask): Int = {
      val tries = ct.tasks combinations (ct.tasks.length - 1)
      val solutions = tries map {lt => solve(CompositeTask(lt))}
      solutions.max
    }

    def cluster(taskList: List[Task]): List[Task] = taskList match {
      case Nil => Nil
      case h :: t =>
        val (m, o) = t span(_ overlap h)

        if(m.isEmpty)
          h :: cluster(o)
        else
          cluster((h merge m) :: o)
    }

    def trim(taskList: List[Task]) = {
      def trimOneWay(taskList: List[Task]) : List[Task] = taskList match {
        case Nil => Nil
        case h :: t if t exists h.contains => trimOneWay(t)
        case h :: t => h :: trimOneWay(t)
      }

      trimOneWay(trimOneWay(taskList).reverse)
    }


  }

}


object Solution extends App {
  import supercalculateur._

  val n = readInt()

  val tasks = CompositeTask(SupCal.parse(for(i <- 0 until n) yield readLine()).toList)


  println(SupCal.solve(tasks))
}