/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Directory now exists. It may have already existed or have just been created. */
trait DirExists extends DoneIO, DirsAbs
{ override def reportStr: String = detailStr -- reportTypeStr
  override def detailStr: String = asStr
}

/** Confirmation that a directory existed. */
class DirExisted(val arrayUnsafe: Array[String]) extends DirExists
{ override def reportTypeStr: String = "Directory existed"
}

object DirExisted
{
  def strs(strs: String*): DirExisted = new DirExisted(strs.toArray)

  def str(inp: String): DirExisted = new DirExisted(DirsPath.strToStrs(inp))
}

/** Confirmation that a directory was created. */
case class DirCreated(val arrayUnsafe: Array[String]) extends DirExists
{ override def reportTypeStr: String = "Directory created"
}

object DirCreated
{
  def strs(strs: String*): DirCreated = new DirCreated(strs.toArray)

  def str(inp: String): DirCreated = new DirCreated(DirsPath.strToStrs(inp))
}