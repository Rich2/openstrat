/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An HTML page updater from an HTML inout or select element. */
abstract class UpdaterInputLike(val page: PageHtmlUpdater) extends InputLike
{ page.inpAcc +%= this

  def listeners: RArr[CallbackUpdater]
  
  /** The number of page elements that have registered to receive updates from this inout. */
  final def numListeners: Int = listeners.length
  
  def listenersListStr: String = listeners.mkStr(_.listenerId, ", ")

  def listenersSummary: String = s"Found $toString with listener IDs: ${listenersListStr}."
}

/** An HTML Input element that updates its page, via JavaScript, rather than though HTTP requests to the server. */
trait UpdaterInput extends UpdaterInputLike, InputHtml
{ override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs
}