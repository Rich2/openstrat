/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utiljvm.*, pDoc.*

object GeomExsJvmApp
{ def main(args: Array[String]): Unit =
  { deb("Starting GeomExsJvmApp")
    val oDir = args.headOption
    println(oDir)
    oDir.foreach{dirStr =>
      fileWrite(dirStr / "documentation.css", CssDocumentation())
      fileWrite(dirStr / "only.css", OnlyCss())
    }
  }
}