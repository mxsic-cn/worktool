package cn.mysic.json;

/**
 * Created with IntelliJ IDEA.
 * User: lxu
 * Date: 12/30/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleAction {
    private ActionType action = ActionType.ALLOW;
    private ReportType report = ReportType.IGNORE;

    public RuleAction(ActionType action, ReportType report) {
        this.action = action;
        this.report = report;
    }

    public RuleAction() {

    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public ReportType getReport() {
        return report;
    }

    public void setReport(ReportType report) {
        this.report = report;
    }

    public boolean checkAction(RuleAction a2) {
        boolean result = false;
        if (this.getAction().equals(a2.getAction()) && this.getReport().equals(a2.getReport())) {
            result = true;
        }
        return result;
    }

}
