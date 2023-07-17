package hodol.hodolpractice.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PageSearch {
    private final int page;
    private final int size;

}
