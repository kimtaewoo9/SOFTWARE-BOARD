package my.board.hotarticle.service.eventhandler;

import lombok.RequiredArgsConstructor;
import my.board.common.event.Event;
import my.board.common.event.EventType;
import my.board.common.event.payload.ArticleCreatedEventPayload;
import my.board.hotarticle.repository.ArticleCreatedTimeRepository;
import my.board.hotarticle.util.TimeCalculatorUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {

	private final ArticleCreatedTimeRepository articleCreatedTimeRepository;

	@Override
	public void handle(Event<ArticleCreatedEventPayload> event) {
		ArticleCreatedEventPayload payload = event.getPayload();
		articleCreatedTimeRepository.createOrUpdate(
			payload.getArticleId(),
			payload.getCreatedAt(),
			TimeCalculatorUtils.calculateDurationToMidnight() // 현재 시간으로부터 자정까지 남은 시간을 저장함 .
		);
	}

	@Override
	public boolean supports(Event<ArticleCreatedEventPayload> event) {
		return event.getType() == EventType.ARTICLE_CREATED;
	}

	@Override
	public Long findArticleId(Event<ArticleCreatedEventPayload> event) {
		// event 의 payload 에서 articleId 를 꺼내줌
		return event.getPayload().getArticleId();
	}
}
