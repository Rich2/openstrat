/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Function object for writing a file. */
object fileWrite
{ /** Writes the String given in the second parameter to the full path and filename given by the first name. Returns a successful message on
   * success. */
  def apply(path: String, fileName: String, str: String): EMon[String] =
  { import java.io._
    var eStr: String = ""
    var opw: Option[FileWriter] = None
    try {
      new File(path).mkdir()
      opw = Some(new FileWriter(new File(path -/- fileName)))
      opw.get.write(str)
    }

    catch { case e: Throwable => eStr = e.toString }
    finally{ opw.foreach(_.close()) }
    if (eStr == "") Good("Successfully written file to " + path -/- fileName) else Bad(Strings(eStr))
  }
}

object homeWrite
{
  def apply(dir: String, fileName: String, str: String): EMon[String] =
  { val h = System.getProperty("user.home")
    fileWrite(h -/- dir , fileName, str)
  }
}

/** Function object to get statements from a Java build resource. */
object statementsFromResource
{ /** Function object apply method to get statements from a Java build resource. */
  def apply(fileName: String): EMon[pParse.Statements] = eTry(io.Source.fromResource(fileName).toArray).flatMap(pParse.srcToEStatements(_, fileName))
}