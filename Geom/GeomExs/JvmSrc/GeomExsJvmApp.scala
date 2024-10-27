/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utiljvm._

object GeomExsJvmApp
{ def main(args: Array[String]): Unit =
  {
    deb("Starting ExsJvmApp")
    val oDir = args.headOption
    println(oDir)
    oDir.foreach{dirStr =>
      fileWrite(dirStr, "documentation.css", CssDocumentation())
      fileWrite(dirStr, "only.css", OnlyCss())
    }
  }
}