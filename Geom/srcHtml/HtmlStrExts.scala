/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

object HtmlStrExts
{
  extension (thisString: String)
  { /** implicit extension method to create HTML b bold element. */
    def b: BHtml = BHtml(thisString)

    /** implicit extension method to create HTML sup superscript element. */
    def sup: SupHtml = SupHtml(thisString)

    /** implicit extension method to create HTML h1 header element. */
    def h1: H1Html = H1Html(thisString)

    /** implicit extension method to create HTML h2 header element. */
    def h2: H2Html = H2Html(thisString)
    
    /** implicit extension method to create HTML h3 header element. */
    def h3: H3Html = H3Html(thisString)
    
    /** implicit extension method to create HTML h4 header element. */
    def h4: H4Html = H4Html(thisString)
    
    /** implicit extension method to create HTML h5 header element. */
    def h5: H5Html = H5Html(thisString)

    /** implicit extension method to create HTML h6 header element. */
    def h6: H6Html = H6Html(thisString)
  }
}