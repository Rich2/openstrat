/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utiljvm.*, java.net.*, java.io.*

trait ServRaw extends App
{
  def doResponse(req: EMon[HttpReq], outPS: PrintStream): Unit

  deb("Starting")
  val servSock: ServerSocket = new ServerSocket(8080)
  var numConns: Int = 0
  while (true) {
    val sock: Socket = servSock.accept()
    val conn = new ConnSesh(numConns, sock)
    val thread = new Thread(conn)
    thread.start()
    numConns += 1
  }
  servSock.close()
  deb("Finishing")

  class ConnSesh(val cNum: Int, val sock: Socket) extends Runnable
  {
    override def run(): Unit =
    {
      val readbuf: BufferedReader = new BufferedReader(new InputStreamReader(sock.getInputStream()))
      val outPS: PrintStream = new PrintStream(new BufferedOutputStream(sock.getOutputStream))
      var reqNum = 0
      while (true) {
        println(s"Req $cNum, $reqNum start")
        var line: String = null
        val acc = StringBuff()
        var continue = true
        while (continue) {
          line = readbuf.readLine
          if (line == null)
            if (acc.nonEmpty) continue = false
            else Thread.sleep(10)
          else if (line == "") continue = false else acc.grow(line)
        }
        val req = HttpReq(acc)
        doResponse(req, outPS)


        reqNum += 1
      }
      readbuf.close
    }
  }
}


