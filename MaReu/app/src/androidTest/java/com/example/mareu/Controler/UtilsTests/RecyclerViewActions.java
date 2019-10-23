package com.example.mareu.Controler.UtilsTests;


import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.PerformException.Builder;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.internal.util.Checks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public final class RecyclerViewActions {
    private static final int NO_POSITION = -1;

    private RecyclerViewActions() {
    }

    public static <VH extends ViewHolder> androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction scrollToHolder(final Matcher<VH> viewHolderMatcher) {
        return new RecyclerViewActions.ScrollToViewAction(viewHolderMatcher);
    }

    public static <VH extends ViewHolder> androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction scrollTo(final Matcher<View> itemViewMatcher) {
        Matcher<VH> viewHolderMatcher = viewHolderMatcher(itemViewMatcher);
        return new RecyclerViewActions.ScrollToViewAction(viewHolderMatcher);
    }

    public static <VH extends ViewHolder> ViewAction scrollToPosition(final int position) {
        return new RecyclerViewActions.ScrollToPositionViewAction(position);
    }

    public static <VH extends ViewHolder> androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction actionOnItem(final Matcher<View> itemViewMatcher, final ViewAction viewAction) {
        Matcher<VH> viewHolderMatcher = viewHolderMatcher(itemViewMatcher);
        return new RecyclerViewActions.ActionOnItemViewAction(viewHolderMatcher, viewAction);
    }

    public static <VH extends ViewHolder> androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction actionOnHolderItem(final Matcher<VH> viewHolderMatcher, final ViewAction viewAction) {
        return new RecyclerViewActions.ActionOnItemViewAction(viewHolderMatcher, viewAction);
    }

    public static <VH extends ViewHolder> ViewAction actionOnItemAtPosition(final int position, final ViewAction viewAction) {
        return new RecyclerViewActions.ActionOnItemAtPositionViewAction(position, viewAction);
    }

    private static <T extends VH, VH extends ViewHolder> List<RecyclerViewActions.MatchedItem> itemsMatching(final RecyclerView recyclerView, final Matcher<VH> viewHolderMatcher, int max) {
        Adapter<T> adapter = recyclerView.getAdapter();
        SparseArray<VH> viewHolderCache = new SparseArray();
        List<RecyclerViewActions.MatchedItem> matchedItems = new ArrayList();

        for(int position = 0; position < adapter.getItemCount(); ++position) {
            int itemType = adapter.getItemViewType(position);
            VH cachedViewHolder = (VH) viewHolderCache.get(itemType);
            if (null == cachedViewHolder) {
                cachedViewHolder = adapter.createViewHolder(recyclerView, itemType);
                viewHolderCache.put(itemType, cachedViewHolder);
            }

            adapter.bindViewHolder((T) cachedViewHolder, position);
            if (viewHolderMatcher.matches(cachedViewHolder)) {
                matchedItems.add(new RecyclerViewActions.MatchedItem(position, HumanReadables.getViewHierarchyErrorMessage(cachedViewHolder.itemView, (List)null, (new StringBuilder(58)).append("\n\n*** Matched ViewHolder item at position: ").append(position).append(" ***").toString(), (String)null)));
                adapter.onViewRecycled((T) cachedViewHolder);
                if (matchedItems.size() == max) {
                    break;
                }
            } else {
                adapter.onViewRecycled((T) cachedViewHolder);
            }
        }

        return matchedItems;
    }

    private static <VH extends ViewHolder> Matcher<VH> viewHolderMatcher(final Matcher<View> itemViewMatcher) {
        return new TypeSafeMatcher<VH>() {
            public boolean matchesSafely(ViewHolder viewHolder) {
                return itemViewMatcher.matches(viewHolder.itemView);
            }

            public void describeTo(Description description) {
                description.appendText("holder with view: ");
                itemViewMatcher.describeTo(description);
            }
        };
    }

    private static class MatchedItem {
        public final int position;
        public final String description;

        private MatchedItem(int position, String description) {
            this.position = position;
            this.description = description;
        }

        public String toString() {
            return this.description;
        }
    }

    private static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;

        private ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed());
        }

        public String getDescription() {
            int var1 = this.position;
            return (new StringBuilder(44)).append("scroll RecyclerView to position: ").append(var1).toString();
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView)view;
            recyclerView.scrollToPosition(this.position);
            uiController.loopMainThreadUntilIdle();
        }
    }

    private static final class ScrollToViewAction<VH extends ViewHolder> implements androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction {
        private final Matcher<VH> viewHolderMatcher;
        private final int atPosition;

        private ScrollToViewAction(Matcher<VH> viewHolderMatcher) {
            this(viewHolderMatcher, -1);
        }

        private ScrollToViewAction(Matcher<VH> viewHolderMatcher, int atPosition) {
            this.viewHolderMatcher = viewHolderMatcher;
            this.atPosition = atPosition;
        }

        public androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction atPosition(int position) {
            Checks.checkArgument(position >= 0, "%d is used as an index - must be >= 0", new Object[]{position});
            return new RecyclerViewActions.ScrollToViewAction(this.viewHolderMatcher, position);
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed());
        }

        public String getDescription() {
            if (this.atPosition == -1) {
                String var1 = String.valueOf(this.viewHolderMatcher);
                return (new StringBuilder(24 + String.valueOf(var1).length())).append("scroll RecyclerView to: ").append(var1).toString();
            } else {
                return String.format("scroll RecyclerView to the: %dth matching %s.", this.atPosition, this.viewHolderMatcher);
            }
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView)view;

            try {
                int maxMatches = this.atPosition == -1 ? 2 : this.atPosition + 1;
                int selectIndex = this.atPosition == -1 ? 0 : this.atPosition;
                List<RecyclerViewActions.MatchedItem> matchedItems = RecyclerViewActions.itemsMatching(recyclerView, this.viewHolderMatcher, maxMatches);
                if (selectIndex >= matchedItems.size()) {
                    throw new RuntimeException(String.format("Found %d items matching %s, but position %d was requested.", matchedItems.size(), this.viewHolderMatcher.toString(), this.atPosition));
                } else if (this.atPosition == -1 && matchedItems.size() == 2) {
                    StringBuilder ambiguousViewError = new StringBuilder();
                    ambiguousViewError.append(String.format("Found more than one sub-view matching %s", this.viewHolderMatcher));
                    Iterator var8 = matchedItems.iterator();

                    while(var8.hasNext()) {
                        RecyclerViewActions.MatchedItem item = (RecyclerViewActions.MatchedItem)var8.next();
                        String var10 = String.valueOf(item);
                        ambiguousViewError.append((new StringBuilder(1 + String.valueOf(var10).length())).append(var10).append("\n").toString());
                    }

                    throw new RuntimeException(ambiguousViewError.toString());
                } else {
                    recyclerView.scrollToPosition(((RecyclerViewActions.MatchedItem)matchedItems.get(selectIndex)).position);
                    uiController.loopMainThreadUntilIdle();
                }
            } catch (RuntimeException var11) {
                throw (new Builder()).withActionDescription(this.getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(var11).build();
            }
        }
    }

    private static final class ActionOnItemAtPositionViewAction<VH extends ViewHolder> implements ViewAction {
        private final int position;
        private final ViewAction viewAction;

        private ActionOnItemAtPositionViewAction(int position, ViewAction viewAction) {
            this.position = position;
            this.viewAction = viewAction;
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed());
        }

        public String getDescription() {
            String var1 = this.viewAction.getDescription();
            int var2 = this.position;
            return (new StringBuilder(79 + String.valueOf(var1).length())).append("actionOnItemAtPosition performing ViewAction: ").append(var1).append(" on item at position: ").append(var2).toString();
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView)view;
            (new RecyclerViewActions.ScrollToPositionViewAction(this.position)).perform(uiController, view);
            uiController.loopMainThreadUntilIdle();
            VH viewHolderForPosition = (VH) recyclerView.findViewHolderForPosition(this.position);
            Builder var10000;
            if (null == viewHolderForPosition) {
                var10000 = (new Builder()).withActionDescription(this.toString()).withViewDescription(HumanReadables.describe(view));
                int var7 = this.position;
                throw var10000.withCause(new IllegalStateException((new StringBuilder(39)).append("No view holder at position: ").append(var7).toString())).build();
            } else {
                View viewAtPosition = viewHolderForPosition.itemView;
                if (null == viewAtPosition) {
                    var10000 = (new Builder()).withActionDescription(this.toString()).withViewDescription(HumanReadables.describe(viewAtPosition));
                    int var6 = this.position;
                    throw var10000.withCause(new IllegalStateException((new StringBuilder(32)).append("No view at position: ").append(var6).toString())).build();
                } else {
                    this.viewAction.perform(uiController, viewAtPosition);
                }
            }
        }
    }

    private static final class ActionOnItemViewAction<VH extends ViewHolder> implements androidx.test.espresso.contrib.RecyclerViewActions.PositionableRecyclerViewAction {
        private final Matcher<VH> viewHolderMatcher;
        private final ViewAction viewAction;
        private final int atPosition;
        private final RecyclerViewActions.ScrollToViewAction<VH> scroller;

        private ActionOnItemViewAction(Matcher<VH> viewHolderMatcher, ViewAction viewAction) {
            this(viewHolderMatcher, viewAction, -1);
        }

        private ActionOnItemViewAction(Matcher<VH> viewHolderMatcher, ViewAction viewAction, int atPosition) {
            this.viewHolderMatcher = (Matcher) Checks.checkNotNull(viewHolderMatcher);
            this.viewAction = (ViewAction)Checks.checkNotNull(viewAction);
            this.atPosition = atPosition;
            this.scroller = new RecyclerViewActions.ScrollToViewAction(viewHolderMatcher, atPosition);
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed());
        }

        public RecyclerViewActions.ActionOnItemViewAction atPosition(int position) {
            Checks.checkArgument(position >= 0, "%d is used as an index - must be >= 0", new Object[]{position});
            return new RecyclerViewActions.ActionOnItemViewAction(this.viewHolderMatcher, this.viewAction, position);
        }

        public String getDescription() {
            return this.atPosition == -1 ? String.format("performing ViewAction: %s on item matching: %s", this.viewAction.getDescription(), this.viewHolderMatcher) : String.format("performing ViewAction: %s on %d-th item matching: %s", this.viewAction.getDescription(), this.atPosition, this.viewHolderMatcher);
        }

        public void perform(UiController uiController, View root) {
            RecyclerView recyclerView = (RecyclerView)root;

            try {
                this.scroller.perform(uiController, root);
                uiController.loopMainThreadUntilIdle();
                int max = this.atPosition == -1 ? 2 : this.atPosition + 1;
                int selectIndex = this.atPosition == -1 ? 0 : this.atPosition;
                List<RecyclerViewActions.MatchedItem> matchedItems = RecyclerViewActions.itemsMatching(recyclerView, this.viewHolderMatcher, max);
                RecyclerViewActions.actionOnItemAtPosition(((RecyclerViewActions.MatchedItem)matchedItems.get(selectIndex)).position, this.viewAction).perform(uiController, root);
                uiController.loopMainThreadUntilIdle();
            } catch (RuntimeException var7) {
                throw (new Builder()).withActionDescription(this.getDescription()).withViewDescription(HumanReadables.describe(root)).withCause(var7).build();
            }
        }
    }

    public interface PositionableRecyclerViewAction extends ViewAction {
        RecyclerViewActions.PositionableRecyclerViewAction atPosition(int position);
    }
}
