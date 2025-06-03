package my.board.hotarticle.service.eventhandler;

import lombok.RequiredArgsConstructor;
import my.board.common.event.Event;
import my.board.common.event.EventType;
import my.board.common.event.payload.ArticleViewEventPayload;
import my.board.hotarticle.repository.ArticleViewCountRepository;
import my.board.hotarticle.util.TimeCalculatorUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleViewedEventHandler implements EventHandler<ArticleViewEventPayload> {

	private final ArticleViewCountRepository articleViewCountRepository;

	@Override
	public void handle(Event<ArticleViewEventPayload> event) {
		ArticleViewEventPayload payload = event.getPayload();
		articleViewCountRepository.createOrUpdate(
			payload.getArticleId(),
			payload.getArticleViewCount(),
			TimeCalculatorUtils.calculateDurationToMidnight()
		);
	}

	@Override
	public boolean supports(Event<ArticleViewEventPayload> event) {
		return event.getType() == EventType.ARTICLE_VIEWED;
	}

	@Override
	public Long findArticleId(Event<ArticleViewEventPayload> event) {
		return event.getPayload().getArticleId();
	}
}
