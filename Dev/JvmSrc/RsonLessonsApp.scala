/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, webjvm.*

@main def RsonLessonsApp =
{ deb("RsonLessons App")
  
  projPathDo { path =>    
    val path1 = path / "Dev/target"
    path1.writeRson("lessons.rson", learn.LessonsLaunch.rsonText)
  }
}