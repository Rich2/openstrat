package ostrat

object TestApp extends App
{
  val ss = "Seq[Int](1, 2, 3)".findType[List[Int]]
  deb(ss.toString)
}