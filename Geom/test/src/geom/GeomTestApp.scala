/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utiljvm.*, pWeb.*

@main def TestHtmlApp =
{
  openstratPath.forFold(err => deb(err.toString)){(path: DirsAbs) =>
    debvar(path)
    
  }
}