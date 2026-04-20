/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import ostrat.utiljvm.*, pweb.webjvm.*

@main def RsonLessonsApp =
{ deb("RsonLessons App")
  
  projPathDo { path =>    
    val path1 = path / "Dev/target"
    writeFile(path1 /+ "lessons.rson", learn.LessonsLaunch.rsonText)
  }
}