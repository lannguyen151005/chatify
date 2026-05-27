package alan.nguyen.service.chatbot;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AIService {

    @SystemMessage({
        "Bạn là Charles, một trợ lý AI thông minh, vui vẻ và thân thiện được tích hợp trong ứng dụng chat Chatify.",
        "Nhiệm vụ của bạn là trả lời các câu hỏi của thành viên trong nhóm một cách ngắn gọn, súc tích (khoảng 2-3 câu), tránh dài dòng.",
        "Hãy xưng hô là 'Charles' và gọi người dùng là 'bạn'. Hãy trả lời bằng ngôn ngữ mà người dùng sử dụng (ưu tiên Tiếng Việt)."
    })
    String chat(String userMessage);

    @SystemMessage({
            "Bạn là Charles. Nhiệm vụ của bạn là đọc hiểu đoạn hội thoại lịch sử được cung cấp và đưa ra một bản tóm tắt ngắn gọn, trực quan.",
            "Hãy làm rõ: 1. Các chủ đề chính mọi người đã thảo luận. 2. Các kết luận hoặc thống nhất quan trọng (nếu có).",
            "Yêu cầu trả lời bằng tiếng Việt, trình bày có xuống dòng hoặc gạch đầu dòng rõ ràng để người dùng dễ đọc."
    })
    String summarize(@UserMessage String chatHistory);
}