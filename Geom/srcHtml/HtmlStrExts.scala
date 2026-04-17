/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

object HtmlStrExts
{
  extension (thisString: String)
  { /** implicit extension method to create HTML b bold element. */
    def b: BHtml = BHtml(thisString)

    /** implicit extension method to create HTML sup superscript element. */
    def sup: SupHtml = SupHtml(thisString)
  }
}