/**
 * Created by ������������� on 08.10.2015.
 */
abstract class HtmlWrap {
    static String getHead(String title, String contentType, long contentLength){
        StringBuilder s = new StringBuilder();
        s.append("HTTP/1.0 200 OK\r\n");
        //���������� ����������� ���������, ��� � �����
        s.append("Content-Type: " + contentType + "\r\n"); //text/html
        if (contentLength > 0)
            s.append("Content-Length: " + contentLength);
        //������ ������ �������� ��������� �� ����
        s.append("\r\n");

        // ���������
        s.append("<!DOCTYPE html>\n");
        s.append("<html>\n");

        s.append("  <head>\n");
        s.append("    <meta charset=\"win-1251\"></meta>\n");
        s.append("    <title>" + title + "</title>\n");
        s.append("  </head>\n");

        return s.toString();
    };
    static String getTail(){
        return "</html>";
    }
}
